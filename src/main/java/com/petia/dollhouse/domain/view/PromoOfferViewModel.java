package com.petia.dollhouse.domain.view;

import com.petia.dollhouse.domain.service.PromoOfferServiceModel;

import java.math.BigDecimal;
import java.util.List;

    public class PromoOfferViewModel {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private BigDecimal price;

        public PromoOfferViewModel() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
