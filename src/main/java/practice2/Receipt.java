package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    /**
     * 计算并返回总价
     * @param products 商品列表 （商品编码，价格，折扣）
     * @param items 订单 （商品编码，数量）
     * @return
     */
    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        //items价格
        BigDecimal subTotal = calculateSubtotal(products, items);
        //税
        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);
        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 根据product获得orderItem
     * @param items
     * @param product
     * @return
     */
    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }

    /**
     * 获得所有items 折扣前价格 ----> 折扣后价格
     * @param products
     * @param items
     * @return
     */
    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = getItemTotal(product,item);
            //折扣
            BigDecimal reducedPrice = getItemTotal(product,item).multiply(product.getDiscountRate());
            subTotal = subTotal.add(itemTotal).subtract(reducedPrice);
        }
        return subTotal;
    }
    /**
     * 获得一个 item 的价钱，单价 * 数量
     * @param product
     * @param item
     * @return
     */
    private BigDecimal getItemTotal(Product product,OrderItem item){
        return product.getPrice().multiply(new BigDecimal(item.getCount()));
    }
}
