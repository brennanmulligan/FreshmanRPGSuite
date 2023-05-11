import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:meta/meta.dart';

import '../../../repository/login_repository/login_repository.dart';
import '../../../repository/login_repository/login_with_credentials_request'
    '.dart';
import '../../../repository/login_repository/login_with_credentials_response'
    '.dart';
import '../../objectives-list/dashboard_view.dart';

part 'login_event.dart';

part 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final LoginRepository loginRepository;
  final BuildContext context;
  bool _testing = false;

  set testing(bool value) {
    _testing = value;
  }

  late int playerID;

  LoginBloc({required this.context, required this.loginRepository})
      : super(LoginInitial()) {
    on<SendLoginEvent>((event, emit) async {
      emit(LoginLoading());
      LoginWithCredentialsResponse response = await loginRepository.loginPlayer(
          LoginWithCredentialsRequest(
              username: event.username, password: event.password));
      if (response.success == false) {
        emit(LoginFailed(response));
        return;
      }
      playerID = response.playerID;
      emit(LoginComplete(response));
    });
  }

  @override
  void onTransition(Transition<LoginEvent, LoginState> transition) {
    super.onTransition(transition);
    if (transition.nextState is LoginComplete) {
      try {
        Navigator.of(context).push(MaterialPageRoute(
            builder: (context) => ObjectivesListView(playerID)));
      } on FlutterError catch (e) {
        if (!_testing) {
          rethrow;
        }
      }
    }
  }
}


