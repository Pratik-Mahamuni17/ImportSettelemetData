package com.intugratic.entities;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "additions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Additions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Net Additions: cancellation refund OR tip for kitchen staff
    @Column(name = "net_additions", precision = 15, scale = 2)
    private BigDecimal netAdditions;
}

