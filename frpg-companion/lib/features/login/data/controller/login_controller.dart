import 'dart:io';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:frpg_companion/features/login/data/data.dart';
import 'package:frpg_companion/features/objective/objective.dart';
part 'login_controller.freezed.dart';

/// Create login states for the controller
@freezed
class LoginState with _$LoginState {
  ///
  /// Manages state for login
  ///
  const factory LoginState({
    @Default(-1) playerID,
    @Default(true) userHasInvalidCred,
    @Default("") username,
    @Default("") password,
    @Default(false) isLoading,
    LoginWithCredentialsResponse? loginResponse,
  }) = _LoginState;

  ///
  /// Create a private singleton instance of the state.
  ///
  const LoginState._();
}

///
/// Create a controller for all login data.
///
class LoginController extends StateNotifier<LoginState> {
  final LoginRepository _repository;

  ///
  /// Constructor
  ///
  LoginController({
    required LoginRepository repository,
    LoginState? state,
  })  : _repository = repository,
        super(state ?? const LoginState());

  ///
  /// Attempt to login
  ///
  Future<void> loginWithCredentials(String username, String password) async {
    state = state.copyWith(
      isLoading: true,
      username: username,
      password: password,
    );

    final response = await _repository.loginWithCredentials(
      request: LoginWithCredentialsRequest(
        username: username,
        password: password,
      ),
    );

    /// When we get a response, create an object and set its state.
    LoginWithCredentialsResponse loginResponse = await response.when(
      data: (data) => data,
      failure: (failure) => const LoginWithCredentialsResponse(
        playerID: -1,
      ),
    );

    /// Update the state.
    state = state.copyWith(
      userHasInvalidCred: loginResponse.playerID == -1,
      playerID: loginResponse.playerID,
      loginResponse: loginResponse,
      isLoading: false,
    );
  }

  ///
  /// Get the login response.
  ///
  LoginWithCredentialsResponse? get loginWithCredentialsResponse =>
      state.loginResponse;

  ///
  /// Get the Player ID
  ///
  num get playerID => state.playerID;

  ///
  /// Get UserHasInvalidResponse
  ///
  bool get userHasInvalidCred => state.userHasInvalidCred;

  ///
  /// Get the Username
  ///
  String get username => state.username;

  ///
  ///
  ///
  bool get isLoading => state.isLoading;
}
