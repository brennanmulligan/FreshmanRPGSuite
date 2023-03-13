part of 'change_password_bloc.dart';

/// The states that the ChangePasswordBloc can be in.

@immutable
abstract class ChangePasswordState extends Equatable {
  @override
  List<Object> get props => [];
}

class ChangePasswordInitial extends ChangePasswordState {}

class ChangePasswordLoading extends ChangePasswordState {}

class ChangePasswordComplete extends ChangePasswordState {
  final BasicResponse response;

  ChangePasswordComplete(this.response);

  @override
  List<Object> get props => [response];
}
