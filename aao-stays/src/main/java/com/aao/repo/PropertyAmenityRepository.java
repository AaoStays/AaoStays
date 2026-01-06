public interface PropertyAmenityRepository
        extends JpaRepository<PropertyAmenity, Long> {

    List<PropertyAmenity> findByProperty_PropertyId(Long propertyId);

    boolean existsByProperty_PropertyIdAndAmenity_AmenityId(
            Long propertyId, Long amenityId
    );

    void deleteByProperty_PropertyIdAndAmenity_AmenityId(
            Long propertyId, Long amenityId
    );
}
