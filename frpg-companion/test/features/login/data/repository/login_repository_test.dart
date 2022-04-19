import 'package:frpg_companion/features/login/login.dart';
import 'package:frpg_networking_api/networking/result/result.dart';
import 'package:mockito/mockito.dart';
import 'package:test/test.dart';

import '../../login_mock.mocks.dart';

void main() {
  late LoginRepository _repository;
  late MockLoginWithCredentialsDatasource mockLoginWithCredentialsDatasource;
  late MockLogoutDatasource mockLogoutDatasource;

  setUp(() {
    mockLoginWithCredentialsDatasource = MockLoginWithCredentialsDatasource();
    mockLogoutDatasource = MockLogoutDatasource();
    _repository = LoginRepositoryHTTP(
      loginWithCredentialsDatasource: mockLoginWithCredentialsDatasource,
      logoutDatasource: mockLogoutDatasource,
    );
  });

  group('Test LoginWithCredentialsDatasource.', () {
    test('Test for ResultData.', () async {
      const expected = LoginWithCredentialsResponse(
        playerID: 1,
      );

      when(mockLoginWithCredentialsDatasource.loginWithCredentials(
        request: anyNamed('request'),
      )).thenAnswer((realInvocation) async => expected);

      final actual = await _repository.loginWithCredentials(
        request: const LoginWithCredentialsRequest(
          username: "john",
          password: "pw",
        ),
      );

      verify(mockLoginWithCredentialsDatasource.loginWithCredentials(
        request: anyNamed('request'),
      ));
      expect(actual, Result.data(data: expected));
    });

    test('Test for ResultFailure', () async {
      when(mockLoginWithCredentialsDatasource.loginWithCredentials(
              request: anyNamed('request')))
          .thenThrow(Exception());

      final actual = await _repository.loginWithCredentials(
        request: const LoginWithCredentialsRequest(
          username: "bob",
          password: "nah",
        ),
      );

      expect(actual, isA<ResultFailure>());
    });
  });
}
