import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';
import 'package:mockito/annotations.dart';

@GenerateMocks([
  ServiceClient,
  LoginWithCredentialsDatasource,
  LogoutDatasource,
  LoginRepository,
])
void main() {}
