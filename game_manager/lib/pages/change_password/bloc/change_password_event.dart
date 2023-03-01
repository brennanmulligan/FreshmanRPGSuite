part of 'change_password_bloc.dart';

@immutable
abstract class ChangePasswordEvent {}

class SendChangePasswordEvent extends ChangePasswordEvent {
  final String playerName;
  final String password;

  SendChangePasswordEvent(this.playerName, this.password);

  @override
  String toString() {
    return 'SendChangePasswordEvent(playerName: $playerName, password: $password)';
  }
}
