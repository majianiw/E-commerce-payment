package com.imooc.mall.service.impl;

import com.google.gson.Gson;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ProductStatusEnum;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartProductVo;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date: create in 22:45 2023/2/20
 * @describe:
 */
@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    //购物车的数量初始为1,后面递增
    Integer quantity = 1;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //判断商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //商品在售状态
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_DELETE);
        }
        //商品库存是否足够
        if (product.getStock() < 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //所有校验完毕  就写入reids
        /**
           3个string的含义：key:cart_uid  ex: cart_1
           redis的哈希结构,redis的key 后面两个参数是hash结构，
           key-value(productId-product对象[{"product":27,"quantity":3,"productSelected":true}])
        */
        //添加商品的逻辑是没添加一次 默认购物车为1，如果购物车(redis)中已经有了要添加的商品，那么需要累加，所以先读出redis中的数据
        // 获取redis中的值
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        //CART_REDIS_KEY_TEMPLATE: 上面有定义 是redis中存储数据的名字
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Cart cart;
        //String.valueOf(product.getId()):把Integer转为字符
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        if (!StringUtils.hasText(value)) {
            //没有该商品
            cart = new Cart(product.getId(), quantity, form.getSelected());
        } else {
            //已经有了,数量+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey,
                String.valueOf(product.getId()), gson.toJson(cart));
//        不用map存储在redis
//        opsForHash.set(String.format(CART_REDIS_KEY_Template, uid),
//                gson.toJson(new Cart(product.getId(), quantity, form.getSelected())));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //是否全选
        boolean selectAll = true;
        //购物车的总数
        Integer cartTotalQuantity = 0;
        //购物车总价
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        //获取redis中的hash结构中的key-value
        Map<String, String> entries = opsForHash.entries(redisKey);
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            //获取redis中的hash结构中的key(productId)
            Integer productId = Integer.parseInt(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //TODO 需要优化 尽量不要在for中查询数据库 使用mysql的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                //传入需要的参数
                CartProductVo cartProductVo = new CartProductVo(productId, cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.isProductSelected());
                cartProductVoList.add(cartProductVo);

                if (!cart.isProductSelected()) {
                    selectAll = false;
                }
                //计算总价(只计算选中的)
                if (cart.isProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            cartTotalQuantity += cart.getQuantity();

        }
        cartVo.setSelectAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (!StringUtils.hasText(value)) {
            //没有该商品 ,数据有问题
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //已经有了,数量+1
        Cart cart = gson.fromJson(value, Cart.class);
        if (form.getQuantity() != null && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setProductSelected(form.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (!StringUtils.hasText(value)) {
            //没有该商品 ,数据有问题
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid){
        Integer sum =  listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    @Override
    public List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(),Cart.class));
        }
        return cartList;
    }
}
