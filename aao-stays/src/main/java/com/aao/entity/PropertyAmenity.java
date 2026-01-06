@Entity
@Table(name = "amenity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    private String amenityName;
    private Boolean isActive;

    @OneToMany(mappedBy = "amenity")
    private List<PropertyAmenity> properties;
}
