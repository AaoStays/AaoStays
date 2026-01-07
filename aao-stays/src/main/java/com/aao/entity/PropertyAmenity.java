@Entity
@Table(name = "property_amenity",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"property_id", "amenity_id"})
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyAmenityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;

    private Boolean isAvailable;

    private LocalDateTime assignedAt;

    @PrePersist
    void onCreate() {
        this.assignedAt = LocalDateTime.now();
    }
}
