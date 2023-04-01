part of 'login_bloc.dart';

@immutable
abstract class LoginState extends Equatable {
  @override
  List<Object> get props => [];
}

class LoginInitial extends LoginState {}

class LoginLoading extends LoginState {}

class LoginComplete extends LoginState {
  final LoginWithCredentialsResponse response;

  LoginComplete(this.response);

  @override
  List<Object> get props => [response];
}