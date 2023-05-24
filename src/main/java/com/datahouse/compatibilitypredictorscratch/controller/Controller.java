package com.datahouse.compatibilitypredictorscratch.controller;

import com.datahouse.compatibilitypredictorscratch.entity.*;
import com.datahouse.compatibilitypredictorscratch.service.ApplicantScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

   private final ApplicantScoringService applicantScoringService;

   @Autowired
   public Controller(ApplicantScoringService applicantScoringService) {
      this.applicantScoringService = applicantScoringService;
   }

   @PostMapping("/")
   public List<ScoredApplicant> scoreApplicants(@RequestBody TeamAndApplicants teamAndApplicants) {
      // calculate team average attribute scores
      List<TeamMember> team = teamAndApplicants.getTeam();
      Attributes avgTeamAttributes = applicantScoringService.calculateAvgTeamAttributes(team);

      List<ScoredApplicant> scoredApplicants = new ArrayList<>();
      // calculate compatibility for each applicant, considering the current team and their attributes
      for(Applicant applicant : teamAndApplicants.getApplicants()) {
         ScoredApplicant applicantScore = applicantScoringService.calculateApplicantScore(
                 applicant,
                 team,
                 avgTeamAttributes);
         double score = applicantScore.getScore();
         // round score to nearest tenths place
         applicantScore.setScore(Math.round(score * 10.0) / 10.0);
         scoredApplicants.add(applicantScore);
      }

      return scoredApplicants;
   }

}
