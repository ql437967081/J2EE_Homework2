package edu.nju.wsql.controller;

import edu.nju.wsql.beans.CartBean;
import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.model.Goods;
import edu.nju.wsql.service.ClientService;
import edu.nju.wsql.service.GoodsService;
import edu.nju.wsql.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goods_list")
public abstract class GoodsListController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ClientService clientService;

    private double checkBalance(String id) {
        return clientService.checkBalance(Integer.parseInt(id));
    }

    public abstract OrderService getOrderService();

    @GetMapping
    public String showGoods(HttpServletRequest request, HttpSession session) {

        CartBean cart = (CartBean) session.getAttribute("cart");

        if (cart == null) {
            cart = new CartBean();
            cart.setOrder(new HashMap<>());
            session.setAttribute("cart", cart);
        }

        cart.setList(goodsService.getGoodsList());

        request.setAttribute("balance", checkBalance((String) session.getAttribute("login")));

        return "/shopping/shopping.jsp";
    }

    @PostMapping
    public String buyGoods(HttpServletRequest request, HttpSession session) {
        CartBean cart = (CartBean) session.getAttribute("cart");

        Map<Integer, Integer> order = cart.getOrder();

        List<Goods> list = cart.getList();

        for (Goods goods: list) {
            int gid = (int) goods.getId();
            String gid_str = "" + gid;
            int num = Integer.parseInt(request.getParameter(gid_str));
            if (num == 0)
                order.remove(gid);
            if (num > 0)
                order.put(gid, num);
        }

        OrderService service = getOrderService();
        service.setOrder(order);
        double total = service.getTotal();

        boolean incentive = service.isIncentive();
        double actual = round2(service.getActual());

        String loginValue = (String) session.getAttribute("login");

        service.setClient(Integer.parseInt(loginValue));

        switch (service.pay()) {
            case SUCCESS:
                String incentiveStr = "";
                if (incentive){
                    incentiveStr = "优惠金额" + round2(total - actual) + "元，";
                }
                order.clear();
                request.setAttribute("info", new InfoBean(
                        "支付成功",
                        "支付成功",
                        "订单总价" + round2(total) + "元，" + incentiveStr +
                                "实际支付" + actual + "元，账户还剩" + round2(checkBalance(loginValue)) +"元！",
                        "/goods_list"));
                break;
            case BALANCE_LACK:
                request.setAttribute("info", new InfoBean(
                        "支付失败",
                        "支付失败",
                        "订单总价" + round2(total) + "元，余额（" + round2(checkBalance(loginValue)) +"元）不足！",
                        "/goods_list"));
                break;
            case SYSTEM_BUSY:
                request.setAttribute("info", new InfoBean(
                        "支付失败",
                        "支付失败",
                        "系统繁忙，请返回重新支付！",
                        "/goods_list"));
                break;
        }
        return "/info_page/info.jsp";
    }

    private static double round2(double d){
        return Math.round(d * 100) / 100.0;
    }
}
