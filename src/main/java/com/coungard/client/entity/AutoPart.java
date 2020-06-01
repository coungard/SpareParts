package com.coungard.client.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class AutoPart {

    private Long id;
    private String vendor;
    private String number;
    private String vendorSearch;
    private String numberSearch;
    private String description;
    private BigDecimal price;
    private Integer count;

    public AutoPart(Long id, String vendor, String number, String vendorSearch,
                    String numberSearch, String description, BigDecimal price, Integer count) {
        this.id = id;
        this.vendor = vendor;
        this.number = number;
        this.vendorSearch = vendorSearch;
        this.numberSearch = numberSearch;
        this.description = description;
        this.price = price;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AutoPart autoPart = (AutoPart) o;

        return new EqualsBuilder()
                .append(id, autoPart.id)
                .append(vendor, autoPart.vendor)
                .append(number, autoPart.number)
                .append(vendorSearch, autoPart.vendorSearch)
                .append(numberSearch, autoPart.numberSearch)
                .append(description, autoPart.description)
                .append(price, autoPart.price)
                .append(count, autoPart.count)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(vendor)
                .append(number)
                .append(vendorSearch)
                .append(numberSearch)
                .append(description)
                .append(price)
                .append(count)
                .toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVendorSearch() {
        return vendorSearch;
    }

    public void setVendorSearch(String vendorSearch) {
        this.vendorSearch = vendorSearch;
    }

    public String getNumberSearch() {
        return numberSearch;
    }

    public void setNumberSearch(String numberSearch) {
        this.numberSearch = numberSearch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
