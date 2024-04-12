package waf.fisa.condition.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import waf.fisa.condition.dto.ConditionDto;
import waf.fisa.condition.dto.ConditionReqDto;
import waf.fisa.condition.entity.Condition;
import waf.fisa.condition.repository.ConditionRepository;
import waf.fisa.condition.repository.ConditionRepositoryCustom;
import waf.fisa.grpc.condition.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@GrpcService
@Slf4j
public class GrpcConditionService extends ConditionServiceGrpc.ConditionServiceImplBase {

    private final ConditionRepository conditionRepository;
    private final ConditionRepositoryCustom conditionRepositoryCustom;

    @Override
    public void saveCondition(ConditionReq request, StreamObserver<ConditionResp> responseObserver) {
        Condition entity = Condition.toEntity(request);

        Condition condition = conditionRepository.save(entity);

        responseObserver.onNext(ConditionResp.newBuilder()
                .setId(condition.getId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setAccountId(condition.getAccountId())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void readCondition(ConditionIdReq request, StreamObserver<ConditionResp> responseObserver) {
        Condition condition = conditionRepository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);

        responseObserver.onNext(ConditionResp.newBuilder()
                .setId(condition.getId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setAccountId(condition.getAccountId())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void readAllCondition(Empty request, StreamObserver<ConditionRespList> responseObserver) {
        List<Condition> list = conditionRepository.findAll(); // entity

        responseObserver.onNext(ConditionRespList.newBuilder()
                .addAllConditions(convertToEntity(list))
                .build()
        );
        responseObserver.onCompleted();
    }

    private static List<waf.fisa.grpc.condition.Condition> convertToEntity(List<Condition> list) {
        return list.stream().map(condition -> waf.fisa.grpc.condition.Condition
                .newBuilder()
                .setId(condition.getId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setAccountId(condition.getAccountId())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        ).toList();
    }


    

    private static List<waf.fisa.grpc.condition.Condition> convertToEntityforReadByWhere(List<ConditionDto> list) {
        return list.stream().map(conditionDto -> waf.fisa.grpc.condition.Condition.newBuilder()
                        .setId(conditionDto.getId())
                        .setLocation(conditionDto.getLocation())
                        .setBuildingType(conditionDto.getBuildingType())
                        .setAccountId(conditionDto.getAccountId())
                        .setFee(conditionDto.getFee())
                        .setMoveInDate(conditionDto.getMoveInDate().toString())
                        .setHashtag(conditionDto.getHashtag())
                        .build()
        ).toList();
    }


    @Override
    public void updateCondition(ConditionReqWithId request, StreamObserver<ConditionResp> responseObserver) {

    }

    @Override
    public void deleteCondition(ConditionIdReq request, StreamObserver<ConditionDeleteResp> responseObserver) {
        if (!conditionRepository.existsById(request.getId())) {
            responseObserver.onNext(ConditionDeleteResp.newBuilder()
                    .setMsg("It doesnt exist.")
                    .build());
            responseObserver.onCompleted();
        } else {
            conditionRepository.deleteById(request.getId());
            responseObserver.onNext(ConditionDeleteResp.newBuilder()
                    .setMsg("It was deleted.")
                    .build());
            responseObserver.onCompleted();
        }
    }
}
