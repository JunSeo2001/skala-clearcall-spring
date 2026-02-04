package com.example.clearcall.service;

import com.example.clearcall.dto.report.ReportRequest;
import com.example.clearcall.dto.report.ReportResponse;
import com.example.clearcall.entity.Report;
import com.example.clearcall.exception.NotFoundException;
import com.example.clearcall.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public List<ReportResponse> getAll() {
        return reportRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ReportResponse getById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
        return toResponse(report);
    }

    @Transactional
    public ReportResponse create(ReportRequest request, Long userId) {
        Report report = new Report();
        report.setTitle(request.title());
        report.setContent(request.content());
        report.setCreatedByUserId(userId);
        return toResponse(reportRepository.save(report));
    }

    @Transactional
    public ReportResponse update(Long id, ReportRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
        report.setTitle(request.title());
        report.setContent(request.content());
        return toResponse(report);
    }

    @Transactional
    public void delete(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
        reportRepository.delete(report);
    }

    private ReportResponse toResponse(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getTitle(),
                report.getContent(),
                report.getCreatedAt(),
                report.getCreatedByUserId()
        );
    }
}
