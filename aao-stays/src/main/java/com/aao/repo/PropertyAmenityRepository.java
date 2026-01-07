public interface PropertyAmenityRepository
        extends JpaRepository<PropertyAmenity, Long> {

    boolean existsByProperty_PropertyIdAndAmenity_AmenityId(
            Long propertyId, Long amenityId);

    List<PropertyAmenity> findByProperty_PropertyId(Long propertyId);

    List<PropertyAmenity> findByAmenity_AmenityId(Long amenityId);

    void deleteByProperty_PropertyIdAndAmenity_AmenityId(
            Long propertyId, Long amenityId);
}
