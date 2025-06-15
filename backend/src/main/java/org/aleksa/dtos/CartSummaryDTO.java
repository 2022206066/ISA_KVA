package org.aleksa.dtos;

import java.math.BigDecimal;
import java.util.List;

public class CartSummaryDTO {
    private List<ReservationDTO> reservations;
    private BigDecimal totalPrice;
    private int totalItems;

    public CartSummaryDTO() {
    }

    public CartSummaryDTO(List<ReservationDTO> reservations, BigDecimal totalPrice, int totalItems) {
        this.reservations = reservations;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    public List<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDTO> reservations) {
        this.reservations = reservations;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
} 
