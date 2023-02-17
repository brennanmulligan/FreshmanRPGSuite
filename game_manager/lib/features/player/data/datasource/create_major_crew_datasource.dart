import 'package:game_manager/features/player/data/data.dart';
import 'package:frpg_networking_api/lib/networking/service_client/service_client.dart';


abstract class CreateMajorCrewDatasource {
  final ServiceClient sc;

  ///
  /// Constructor
  ///
  const CreateMajorCrewDatasource ({
    required this.sc,
  });

  ///
  /// Command to create a player
  ///
  Future<CreateMajorCrewResponse> createMajorCrew({
    required CreateMajorCrewRequest request,
  });
}