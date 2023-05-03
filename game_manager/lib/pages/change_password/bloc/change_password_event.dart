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
  final String playerName;
  final String newPassword;

  SendChangePasswordEvent(this.playerName, this.newPassword);

  @override
  String toString() {
    return 'SendChangePasswordEvent(playerName: $playerName, password: $newPassword)';
  }
}
