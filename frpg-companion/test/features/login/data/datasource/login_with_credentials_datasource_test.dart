import 'dart:convert';
import 'package:frpg_companion/features/login/data/data.dart';
import 'package:test/test.dart';
import 'package:frpg_companion/features/objective/objective.dart';
import 'package:mockito/mockito.dart';

import '../../login_mock.mocks.dart';

void main() {
  late MockServiceClient mockServiceClient;
  late LoginWithCredentialsDatasource datasource;

  setUp(() {
    mockServiceClient = MockServiceClient();
    datasource = LoginWithCredentialsDatasourceHTTP(
      sc: mockServiceClient,
    );
  });

  group('Test LoginWithCredentialsDatasource', () {
    test('Test for valid response.', () async {
      final testJSONResponse = jsonDecode('''{
        "playerID": 1
      }''');

      final expected = LoginWithCredentialsResponse.fromJson(
        json: testJSONResponse,
      );

      when(
        mockServiceClient.post(
          endpoint: anyNamed('endpoint'),
          body: anyNamed('body'),
        ),
      ).thenAnswer((realInvocation) async => testJSONResponse);

      final actual = await datasource.loginWithCredentials(
        request: const LoginWithCredentialsRequest(
          username: "john",
          password: "pw"
        ),
      );

      verify(mockServiceClient.post(
        endpoint: anyNamed('endpoint'),
        body: anyNamed('body'),
      ));
      expect(actual, expected);
    });
  });
}
