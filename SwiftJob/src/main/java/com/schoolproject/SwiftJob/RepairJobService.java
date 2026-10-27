/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author motho
 */
@Service
public class RepairJobService 
{
    private final RepairJobRepository repository;

    // Receives the repository from Spring Boot.
    public RepairJobService(RepairJobRepository repository) 
    {
        this.repository = repository;
    }

    // Adds a new repair job.
    public RepairJob addJob(RepairJob job) 
    {
        job.setStatus(RepairStatus.RECEIVED);
        job.setDateReceived(LocalDate.now());
        return repository.save(job);
    }

    // Gets all repair jobs.
    public List<RepairJob> getAllJobs() 
    {
        return repository.findAll();
    }

    // Gets one job using its ID.
    public RepairJob getJob(Long id) 
    {
        return repository.findById(id).orElseThrow(() -> new JobNotFoundException(id));
    }

    // Updates the details of an existing job.
    public RepairJob updateJob(Long id, RepairJob newJob) 
    {
        RepairJob oldJob = getJob(id);
        oldJob.setCustomerName(newJob.getCustomerName());
        oldJob.setDevice(newJob.getDevice());
        oldJob.setProblemDescription(newJob.getProblemDescription());
        oldJob.setCost(newJob.getCost());

        // Saves the updated job.
        return repository.save(oldJob);
    }

    // Changes the job to the next status.
    public RepairJob changeStatus(Long id, RepairStatus newStatus) 
    {
        RepairJob job = getJob(id);
        RepairStatus currentStatus = job.getStatus();

        // Checks which status change is allowed.
        boolean allowed = false;

        if (currentStatus == RepairStatus.RECEIVED&& newStatus == RepairStatus.IN_PROGRESS)
        {
            allowed = true;
        } else if (currentStatus == RepairStatus.IN_PROGRESS&& newStatus == RepairStatus.READY)
        {
            allowed = true;
        } else if (currentStatus == RepairStatus.READY&& newStatus == RepairStatus.COLLECTED)
        {
            allowed = true;
        }

        // Stops invalid status changes.
        if (!allowed) 
        {
            throw new BusinessRuleException("You must move the job to the next status.");
        }
        job.setStatus(newStatus);
        return repository.save(job);
    }

    // Deletes a job only if the customer has collected it.
    public void deleteJob(Long id) 
    {
        RepairJob job = getJob(id);

        if (job.getStatus() != RepairStatus.COLLECTED) 
        {
            throw new BusinessRuleException("Only collected jobs can be deleted.");
        }
        repository.delete(job);
    }

    // Finds jobs by status.
    public List<RepairJob> getJobsByStatus(RepairStatus status) 
    {
        return repository.findByStatus(status);
    }

    // Finds READY jobs received before the cutoff date.
    public List<RepairJob> getOverdueJobs(int days) 
    {
        if (days < 1 || days > 365) 
        {
            throw new BusinessRuleException("Enter a number of days between 1 and 365.");
        }
        LocalDate cutoff = LocalDate.now().minusDays(days);
        return repository.findByStatusAndDateReceivedBefore( RepairStatus.READY, cutoff);
    }
}
