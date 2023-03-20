import 'package:bloc_test/bloc_test.dart';
import 'package:game_manager/repository/player/all_crews_response.dart';
import 'package:game_manager/repository/player/all_majors_response.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';
import 'package:test/test.dart';
import 'package:game_manager/pages/get_majors_and_crews/bloc/get_majors_and_crews_bloc.dart';
import 'package:game_manager/repository/player/crews_repository.dart';
import 'package:game_manager/repository/player/majors_repository.dart';

import 'get_majors_and_crews_test.mocks.dart';

class CrewsRepositoryTest extends Mock implements CrewsRepository {}
class MajorsRepositoryTest extends Mock implements MajorsRepository {}

@GenerateMocks([CrewsRepositoryTest])
@GenerateMocks([MajorsRepositoryTest])
Future<void> main() async {
  group('GetMajorsAndCrewsBloc', () {
    late MockCrewsRepositoryTest mockCrewsRepo;
    late MockMajorsRepositoryTest mockMajorsRepo;
    const AllCrewsResponse goodCrewsResponse = AllCrewsResponse(true, crews: []);
    const AllMajorsResponse goodMajorsResponse = AllMajorsResponse(true, majors: []);

    setUp(() {
      mockCrewsRepo = MockCrewsRepositoryTest();
      mockMajorsRepo = MockMajorsRepositoryTest();
    });

    blocTest<GetMajorsAndCrewsBloc, GetMajorsAndCrewsState>(
      'emits [GetMajorsAndCrewsLoading, GetMajorsAndCrewsLoaded] when SendCreateMajorsCrewEvent is added',
      build: () {
        when(mockMajorsRepo.getAllMajors(any))
            .thenAnswer((_) async => goodMajorsResponse);
        when(mockCrewsRepo.getAllCrews(any))
            .thenAnswer((_) async => goodCrewsResponse);
        return GetMajorsAndCrewsBloc(CrewsRepository: mockCrewsRepo, MajorsRepository: mockMajorsRepo);
      },
      act: (bloc) => bloc.add(SendCreateMajorsCrewEvent(1, 1)),
      wait: const Duration(milliseconds: 2000),
      //expect: () => [GetMajorsAndCrewsLoading(), GetMajorCrewComplete(goodMajorsResponse)],
      expect: () => [GetMajorsAndCrewsLoading(), GetMajorCrewComplete(goodMajorsResponse, goodCrewsResponse)],
    );
  });
}
