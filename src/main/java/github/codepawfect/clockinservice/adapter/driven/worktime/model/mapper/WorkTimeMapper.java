package github.codepawfect.clockinservice.adapter.driven.worktime.model.mapper;

import github.codepawfect.clockinservice.adapter.driven.worktime.model.WorkTimeDto;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * WorkTimeMapper is a mapper interface that converts between WorkTime domain model and WorkTimeDto.
 * It uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface WorkTimeMapper {
  WorkTimeMapper INSTANCE = Mappers.getMapper(WorkTimeMapper.class);

  /**
   * Converts a WorkTime domain model to a WorkTimeDto.
   *
   * @param workTime the WorkTime domain model
   * @return the WorkTimeDto
   */
  WorkTimeDto toDto(WorkTime workTime);

  /**
   * Converts a WorkTimeDto to a WorkTime domain model.
   *
   * @param workTimeDto the WorkTimeDto
   * @return the WorkTime domain model
   */
  WorkTime toDomainModel(WorkTimeDto workTimeDto);

  /**
   * Converts a list of WorkTime domain models to a list of WorkTimeDto.
   *
   * @param workTimes the list of WorkTime domain models
   * @return the list of WorkTimeDto
   */
  List<WorkTimeDto> toDtos(List<WorkTime> workTimes);

  /**
   * Converts a list of WorkTimeDto to a list of WorkTime domain models.
   *
   * @param workTimes the list of WorkTimeDto
   * @return the list of WorkTime domain models
   */
  List<WorkTime> toDomainModels(List<WorkTimeDto> workTimes);
}
