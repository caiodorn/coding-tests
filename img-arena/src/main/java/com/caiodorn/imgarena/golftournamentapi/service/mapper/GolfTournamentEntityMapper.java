package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LocalDateTimeMapper.class)
public interface GolfTournamentEntityMapper {

    @Mapping(source = "tournamentId", target = "externalId")
    @Mapping(source = "countryCode", target = "country")
    @Mapping(source = "roundCount", target = "numberOfRounds")
    @Mapping(source = "startDate", target = "startDateTime")
    @Mapping(source = "endDate", target = "endDateTime")
    GolfTournamentEntity toEntity(GolfTournamentTypeAResource source);

    @Mapping(source = "tournamentUUID", target = "externalId")
    @Mapping(source = "golfCourse", target = "courseName")
    @Mapping(source = "competitionName", target = "tournamentName")
    @Mapping(source = "hostCountry", target = "country")
    @Mapping(source = "rounds", target = "numberOfRounds")
    @Mapping(source = "epochStart", target = "startDateTime")
    @Mapping(source = "epochFinish", target = "endDateTime")
    GolfTournamentEntity toEntity(GolfTournamentTypeBResource source);

}
