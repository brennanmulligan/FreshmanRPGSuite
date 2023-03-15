import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

import '../../../repository/player/basic_response.dart';
import '../../../repository/player/change_password_request.dart';
import '../../../repository/player/player_repository.dart';

part 'change_password_event.dart';
part 'change_password_state.dart';

class ChangePasswordBloc extends Bloc<ChangePasswordEvent, ChangePasswordState> {
  late ChangePasswordRequest data;
  final PlayerRepository playerRepository;

  ChangePasswordBloc({
    required this.playerRepository,
  }) : super(ChangePasswordInitial()) {
    on<SendChangePasswordEvent>((event, emit) async {
      emit(ChangePasswordLoading());

      BasicResponse response = await playerRepository.changePassword(ChangePasswordRequest
        (userName: event.name, newPassword: event.newPassword));

      emit(ChangePasswordComplete(response));
    });
  }
}
