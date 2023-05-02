// Mocks generated by Mockito 5.3.2 from annotations
// in game_manager/test/pages/create_player/create_player_test.dart.
// Do not manually edit this file.

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'dart:async' as _i6;

import 'package:dio/dio.dart' as _i2;
import 'package:game_manager/repository/player/all_players_request.dart' as _i9;
import 'package:game_manager/repository/player/all_players_response.dart'
    as _i4;
import 'package:game_manager/repository/player/basic_response.dart' as _i3;
import 'package:game_manager/repository/player/change_player_request.dart'
    as _i8;
import 'package:game_manager/repository/player/create_player_request.dart'
    as _i7;
import 'package:mockito/mockito.dart' as _i1;

import 'create_player_test.dart' as _i5;

// ignore_for_file: type=lint
// ignore_for_file: avoid_redundant_argument_values
// ignore_for_file: avoid_setters_without_getters
// ignore_for_file: comment_references
// ignore_for_file: implementation_imports
// ignore_for_file: invalid_use_of_visible_for_testing_member
// ignore_for_file: prefer_const_constructors
// ignore_for_file: unnecessary_parenthesis
// ignore_for_file: camel_case_types
// ignore_for_file: subtype_of_sealed_class

class _FakeDio_0 extends _i1.SmartFake implements _i2.Dio {
  _FakeDio_0(
    Object parent,
    Invocation parentInvocation,
  ) : super(
          parent,
          parentInvocation,
        );
}

class _FakeBasicResponse_1 extends _i1.SmartFake implements _i3.BasicResponse {
  _FakeBasicResponse_1(
    Object parent,
    Invocation parentInvocation,
  ) : super(
          parent,
          parentInvocation,
        );
}

class _FakeAllPlayersResponse_2 extends _i1.SmartFake
    implements _i4.AllPlayersResponse {
  _FakeAllPlayersResponse_2(
    Object parent,
    Invocation parentInvocation,
  ) : super(
          parent,
          parentInvocation,
        );
}

/// A class which mocks [PlayerRepositoryTest].
///
/// See the documentation for Mockito's code generation for more information.
class MockPlayerRepositoryTest extends _i1.Mock
    implements _i5.PlayerRepositoryTest {
  MockPlayerRepositoryTest() {
    _i1.throwOnMissingStub(this);
  }

  @override
  _i2.Dio get dio => (super.noSuchMethod(
        Invocation.getter(#dio),
        returnValue: _FakeDio_0(
          this,
          Invocation.getter(#dio),
        ),
      ) as _i2.Dio);
  @override
  _i6.Future<_i3.BasicResponse> createPlayer(
          _i7.CreatePlayerRequest? request) =>
      (super.noSuchMethod(
        Invocation.method(
          #createPlayer,
          [request],
        ),
        returnValue: _i6.Future<_i3.BasicResponse>.value(_FakeBasicResponse_1(
          this,
          Invocation.method(
            #createPlayer,
            [request],
          ),
        )),
      ) as _i6.Future<_i3.BasicResponse>);
  @override
  _i6.Future<_i3.BasicResponse> changePassword(
          _i8.ChangePlayerRequest? request) =>
      (super.noSuchMethod(
        Invocation.method(
          #changePassword,
          [request],
        ),
        returnValue: _i6.Future<_i3.BasicResponse>.value(_FakeBasicResponse_1(
          this,
          Invocation.method(
            #changePassword,
            [request],
          ),
        )),
      ) as _i6.Future<_i3.BasicResponse>);
  @override
  _i6.Future<_i4.AllPlayersResponse> getAllPlayers(
          _i9.AllPlayersRequest? request) =>
      (super.noSuchMethod(
        Invocation.method(
          #getAllPlayers,
          [request],
        ),
        returnValue:
            _i6.Future<_i4.AllPlayersResponse>.value(_FakeAllPlayersResponse_2(
          this,
          Invocation.method(
            #getAllPlayers,
            [request],
          ),
        )),
      ) as _i6.Future<_i4.AllPlayersResponse>);
}
