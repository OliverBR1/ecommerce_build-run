package tech.oliver.ecommerce_build.run.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table (name = "tb_users")
public class UserEntiy {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "full_name")
    private String fullName;

    @OneToOne
    @JoinColumn(name = "billing_address_id")
    private BillingAddressEntity billingAddress;

    public UserEntiy() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BillingAddressEntity getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddressEntity billingAddress) {
        this.billingAddress = billingAddress;
    }
}
