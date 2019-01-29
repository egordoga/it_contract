package ua.it_contract.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.it_contract.entity.DataObject;

@Repository
public interface DataObjectRepository extends JpaRepository<DataObject, String> {
}
