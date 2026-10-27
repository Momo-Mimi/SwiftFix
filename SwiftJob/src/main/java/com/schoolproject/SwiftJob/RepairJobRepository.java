/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.schoolproject.SwiftJob;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author motho
 */
@Repository
public interface RepairJobRepository extends JpaRepository <RepairJob, Long>
{
    List<RepairJob> findByStatus(RepairStatus status);
    List<RepairJob> findByStatusAndDateReceivedBefore(RepairStatus status,LocalDate date);
}
