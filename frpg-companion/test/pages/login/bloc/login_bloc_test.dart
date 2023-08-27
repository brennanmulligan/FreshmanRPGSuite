import 'package:companion_app/pages/login/bloc/login_bloc.dart';
import 'package:companion_app/repository/login_repository'
    '/login_with_credentials_response.dart';
import 'package:companion_app/repository/login_repository/login_repository'
    '.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:mockito/mockito.dart';
import 'package:bloc_test/bloc_test.dart';
import 'package:mockito/annotations.dart';

import 'login_bloc_test.mocks.dart';

class LoginRepositoryMock extends Mock implements LoginRepository {}

class MockBuildContext extends Mock implements BuildContext {}

BuildContext createContext() {
  final mockContext = MockBuildContext();
  return mockContext;
}

@GenerateMocks([LoginRepositoryMock, MockBuildContext])
Future<void> main() async {
  late MockLoginRepositoryMock loginRepo;
  final BuildContext mockContext = createContext();

  setUpAll(() {
    loginRepo = MockLoginRepositoryMock();
  });

  group('Login Tests: ', () {
    const LoginWithCredentialsResponse goodResponse =
        LoginWithCredentialsResponse(playerID: 42, success: true, authKey: 'abcdefg');

    blocTest<LoginBloc, LoginState>(
      ' Check flow of states with good input',
      build: () {
        when(loginRepo.loginPlayer(any)).thenAnswer((_) async => goodResponse);
        LoginBloc bloc =
            LoginBloc(loginRepository: loginRepo, context: mockContext);
        bloc.testing = true;
        return bloc;
      },
      act: (bloc) => bloc.add(SendLoginEvent('fred', 'pw')),
      wait: const Duration(milliseconds: 500),
      expect: () => [LoginLoading(), LoginComplete(goodResponse)],
    );
  });
}
