package site.nomoreparties.stellarburgers.params.responses.order.get;

public class GetOrder {
    private boolean success;
    private int total;
    private int totalToday;
    private Orders[] orders;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }

    public Orders[] getOrders() {
        return orders;
    }

    public void setOrders(Orders[] orders) {
        this.orders = orders;
    }
}
