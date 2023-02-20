import 'package:frpg_networking_api/networking/result/result.dart';
import 'package:game_manager/features/player/data/data.dart';

///
/// Template for a majors and crews repository.
///
abstract class MajorsAndCrewsRepository {
  final GetMajorsAndCrewsDatasource getMajorsAndCrewsDatasource;

  ///
  /// Constructor.
  ///
  const MajorsAndCrewsRepository({
    required this.MajorsAndCrewsRepository,
  });

  ///
  /// Template for getting all majors and crews
  ///
  Future<Result<GetMajorsAndCrewsDatasource>> getMajorsAndCrews({
    required GetMajorsAndCrewsRequest request,
  });
}