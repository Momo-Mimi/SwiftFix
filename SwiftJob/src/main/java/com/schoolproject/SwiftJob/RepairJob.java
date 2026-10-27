/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author motho
 */

@Entity
@Table(name = "repair_jobs")
public class RepairJob 
{
    // Primary key. SQL Server generates this ID automatically.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Customer's name (customer_name in the database).
    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name cannot exceed 100 characters")
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    // Device being repaired.
    @NotBlank(message = "Device is required")
    @Size(max = 100, message = "Device cannot exceed 100 characters")
    @Column(name = "device", nullable = false, length = 100)
    private String device;

    // Description of the repair problem.
    @NotBlank(message = "Problem description is required")
    @Size(max = 500, message = "Problem description cannot exceed 500 characters")
    @Column(name = "problem_description", nullable = false, length = 500)
    private String problemDescription;

    // Repair cost. The value cannot be negative.
    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.00", message = "Cost cannot be negative")
    @Digits(integer = 8, fraction = 2, message = "Cost can have up to 8 whole digits and 2 decimal places")
    @Column(name = "cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    // Current repair status.
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RepairStatus status = RepairStatus.RECEIVED;

    // Date the repair was received.
    @NotNull(message = "Date received is required")
    @Column(name = "date_received", nullable = false)
    private LocalDate dateReceived;

    // Sets default values before a new repair job is saved.
    @PrePersist
    public void beforeSaving() {
        if (dateReceived == null) {
            dateReceived = LocalDate.now();
        }

        if (status == null) {
            status = RepairStatus.RECEIVED;
        }
    }

    // Empty constructor required by JPA.
    public RepairJob() 
    {
        
    }
    
    // Sets the date and status before saving a new job.
    @PrePersist
    public void setDefaults() 
    {
        if (dateReceived == null) 
        {
            dateReceived = LocalDate.now();
        }

        if (status == null) 
        {
            status = RepairStatus.RECEIVED;
        }
    }
    
    //Getters
    public Long getId() 
    {
        return id;
    }
    
    public String getCustomerName() 
    {
        return customerName;
    }
    
    public String getDevice() 
    {
        return device;
    }
    
    public String getProblemDescription() 
    {
        return problemDescription;
    }
    
    public BigDecimal getCost() 
    {
        return cost;
    }
    
    public RepairStatus getStatus() 
    {
        return status;
    }
    
    public LocalDate getDateReceived() 
    {
        return dateReceived;
    }
    
    //Setters
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }
    
    public void setDevice(String device) 
    {
        this.device = device;
    }
    
    public void setProblemDescription(String problemDescription) 
    {
        this.problemDescription = problemDescription;
    }
    
    public void setCost(BigDecimal cost) 
    {
        this.cost = cost;
    }
    
    public void setStatus(RepairStatus status)
    {
        this.status = status;
    }
    
    public void setDateReceived(LocalDate dateReceived) 
    {
        this.dateReceived = dateReceived;
    }
}
