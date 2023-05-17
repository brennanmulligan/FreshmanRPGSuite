part of 'login_bloc.dart';

@immutable
abstract class LoginEvent {}

class SendLoginEvent extends LoginEvent {
  final String playerName;
  final String password;

  SendLoginEvent(this.playerName, this.password);

  @override
  String toString() {
    return 'SendLoginEvent(playerName: $playerName, password $password)';
  }
}
