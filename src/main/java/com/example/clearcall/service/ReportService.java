package com.example.clearcall.service;

import com.example.clearcall.client.ReportAnalysisClient;
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
    private final ReportAnalysisClient reportAnalysisClient;

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
    public ReportResponse create(ReportRequest request, Long userId, String authorization) {
        String generatedReport = reportAnalysisClient.generateReport(request.title(), request.content(), authorization);

        Report report = new Report();
        report.setTitle(request.title());
        report.setContent(generatedReport);
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
