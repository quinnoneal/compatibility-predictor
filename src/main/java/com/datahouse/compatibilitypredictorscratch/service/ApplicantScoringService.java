package com.datahouse.compatibilitypredictorscratch.service;

import com.datahouse.compatibilitypredictorscratch.entity.Applicant;
import com.datahouse.compatibilitypredictorscratch.entity.Attributes;
import com.datahouse.compatibilitypredictorscratch.entity.ScoredApplicant;
import com.datahouse.compatibilitypredictorscratch.entity.TeamMember;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantScoringService {
    // adjustable factors to tweak how much you prioritize applicant value / balance added to team
    // Decrease values to increase its importance in hiring process, vice versa
    private final double VALUE_FACTOR = 15;
    private final double BALANCE_FACTOR = 2;

    public Attributes calculateAvgTeamAttributes(List<TeamMember> team) {
        //TODO: loop through all attributes and calculate average
        Attributes avgTeamAttributes = new Attributes();
        float avgCommunication = 0;
        float avgFlexibility = 0;
        float avgPositivity = 0;
        float avgOpenMindedness = 0;

        for(TeamMember member : team) {
            Attributes att = member.getAttributes();

            avgCommunication += att.getCommunication();
            avgFlexibility += att.getFlexibility();
            avgPositivity += att.getPositivity();
            avgOpenMindedness += att.getOpenMindedness();
        }

        avgCommunication /= team.size();
        avgFlexibility /= team.size();
        avgPositivity /= team.size();
        avgOpenMindedness /= team.size();

        avgTeamAttributes.setCommunication(avgCommunication);
        avgTeamAttributes.setFlexibility(avgFlexibility);
        avgTeamAttributes.setPositivity(avgPositivity);
        avgTeamAttributes.setOpenMindedness(avgOpenMindedness);

        return avgTeamAttributes;
    }

    /*
    Predict applicant compatibility with team by calculating how well they balance the teams average score.
    The ideal candidate creates a well-rounded team (Determined by lower standard deviation.)
     */
    public ScoredApplicant calculateApplicantScore(Applicant applicant,
                                                   List<TeamMember> team,
                                                   Attributes avgTeamAttributes) {
        // add applicant to team for calculations
        TeamMember newMember = new TeamMember();
        newMember.setName(applicant.getName());
        newMember.setAttributes(applicant.getAttributes());
        List<TeamMember> newTeam = new ArrayList<>(team);
        newTeam.add(newMember);
        // get new team average of attributes
        Attributes newTeamAvgAttributes = calculateAvgTeamAttributes(newTeam);

        double oldSD = SD(avgTeamAttributes);
        double newSD = SD(newTeamAvgAttributes);

        // represents value added to the team
        double valueAdded = (newTeamAvgAttributes.getSum() - avgTeamAttributes.getSum()) / VALUE_FACTOR;
        // represents how much more or less well-rounded the team is with applicant
        double balanceAdded = (oldSD - newSD) / BALANCE_FACTOR;

        ScoredApplicant score = new ScoredApplicant();
        score.setName(applicant.getName());
        // set score within inclusive range of 0 to 1
        score.setScore(balanceAdded + valueAdded > 0 ?
                        balanceAdded + valueAdded <= 1 ?
                        balanceAdded + valueAdded : 1 : 0);
        return score;
    }

    // source: https://www.geeksforgeeks.org/java-program-to-calculate-standard-deviation/#
    // finds standard deviation of team attribute scores
    private double SD(Attributes atr) {
        List<Float> attList = atr.getAttributeList();
        double standardDeviation = 0.0;
        double sum = 0.0;
        double mean;
        double sq;
        double res;
        int n = attList.size();

        for (Float i : attList) {
            sum += i;
        }

        mean = sum / n;

        for (Float i : attList) {
            standardDeviation
                    = standardDeviation + Math.pow((i - mean), 2);
        }

        sq = standardDeviation / n;
        res = Math.sqrt(sq);
        return res;
    }

}
