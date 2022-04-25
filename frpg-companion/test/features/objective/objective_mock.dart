import 'package:frpg_companion/features/objective/objective.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';
import 'package:mockito/annotations.dart';

@GenerateMocks([
  ServiceClient,
  FetchAllObjectiveDatasource,
  CompleteObjectiveDatasource,
  ObjectiveRepository,
])
void main() {
  // do nothing, generate mocks...
}
