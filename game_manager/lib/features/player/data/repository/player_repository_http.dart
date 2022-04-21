import 'package:frpg_networking_api/networking/failure/implementations/http_failure.dart';
import 'package:frpg_networking_api/networking/result/result.dart';
import 'package:game_manager/features/player/data/data.dart';

///
/// Player repository implementation using HTTP
///
class PlayerRepositoryHTTP extends PlayerRepository {
  ///
  /// Constructor
  ///
  const PlayerRepositoryHTTP({
    required CreatePlayerDatasource createPlayerDatasource,
  }) : super(createPlayerDatasource: createPlayerDatasource);

  ///
  /// Create a player on a remote server
  ///
  @override
  Future<Result<CreatePlayerResponse>> createPlayer({
    required CreatePlayerRequest request}) async {
    try {
      final response = 
        await createPlayerDatasource.createPlayer(request: request);
      return Result.data(data: response);
    } catch (exception, stackTrace) {
      return Result.failure(
        failure: HTTPFailure(
          message: '$exception : $stackTrace'
        ),
      );
    }
  }
}
