part of 'change_password_bloc.dart';

@immutable
abstract class ChangePasswordEvent {}

class GetPlayerNamesForPageEvent extends ChangePasswordEvent {
  GetPlayerNamesForPageEvent();

  @override
  String toString() {
    return 'GetPlayerNamesForPageEvent()';
  }
}

class SendChangePasswordEvent extends ChangePasswordEvent {
  final String name;
  final String newPassword;

  SendChangePasswordEvent(this.name, this.newPassword);

  @override
  String toString() {
    return 'SendChangePasswordEvent(playerName: $name, password: $newPassword)';
  }
}
