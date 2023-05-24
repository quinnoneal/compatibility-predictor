package com.datahouse.compatibilitypredictorscratch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamAndApplicants {
    private List<TeamMember> team;
    private List<Applicant> applicants;
}
