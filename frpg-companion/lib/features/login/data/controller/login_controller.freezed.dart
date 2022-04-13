// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'login_controller.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$LoginStateTearOff {
  const _$LoginStateTearOff();

  _LoginState call(
      {dynamic playerID = -1,
      dynamic userHasInvalidCred = true,
      dynamic username = "",
      dynamic password = "",
      dynamic isLoading = false,
      LoginWithCredentialsResponse? loginResponse}) {
    return _LoginState(
      playerID: playerID,
      userHasInvalidCred: userHasInvalidCred,
      username: username,
      password: password,
      isLoading: isLoading,
      loginResponse: loginResponse,
    );
  }
}

/// @nodoc
const $LoginState = _$LoginStateTearOff();

/// @nodoc
mixin _$LoginState {
  dynamic get playerID => throw _privateConstructorUsedError;
  dynamic get userHasInvalidCred => throw _privateConstructorUsedError;
  dynamic get username => throw _privateConstructorUsedError;
  dynamic get password => throw _privateConstructorUsedError;
  dynamic get isLoading => throw _privateConstructorUsedError;
  LoginWithCredentialsResponse? get loginResponse =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $LoginStateCopyWith<LoginState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoginStateCopyWith<$Res> {
  factory $LoginStateCopyWith(
          LoginState value, $Res Function(LoginState) then) =
      _$LoginStateCopyWithImpl<$Res>;
  $Res call(
      {dynamic playerID,
      dynamic userHasInvalidCred,
      dynamic username,
      dynamic password,
      dynamic isLoading,
      LoginWithCredentialsResponse? loginResponse});
}

/// @nodoc
class _$LoginStateCopyWithImpl<$Res> implements $LoginStateCopyWith<$Res> {
  _$LoginStateCopyWithImpl(this._value, this._then);

  final LoginState _value;
  // ignore: unused_field
  final $Res Function(LoginState) _then;

  @override
  $Res call({
    Object? playerID = freezed,
    Object? userHasInvalidCred = freezed,
    Object? username = freezed,
    Object? password = freezed,
    Object? isLoading = freezed,
    Object? loginResponse = freezed,
  }) {
    return _then(_value.copyWith(
      playerID: playerID == freezed
          ? _value.playerID
          : playerID // ignore: cast_nullable_to_non_nullable
              as dynamic,
      userHasInvalidCred: userHasInvalidCred == freezed
          ? _value.userHasInvalidCred
          : userHasInvalidCred // ignore: cast_nullable_to_non_nullable
              as dynamic,
      username: username == freezed
          ? _value.username
          : username // ignore: cast_nullable_to_non_nullable
              as dynamic,
      password: password == freezed
          ? _value.password
          : password // ignore: cast_nullable_to_non_nullable
              as dynamic,
      isLoading: isLoading == freezed
          ? _value.isLoading
          : isLoading // ignore: cast_nullable_to_non_nullable
              as dynamic,
      loginResponse: loginResponse == freezed
          ? _value.loginResponse
          : loginResponse // ignore: cast_nullable_to_non_nullable
              as LoginWithCredentialsResponse?,
    ));
  }
}

/// @nodoc
abstract class _$LoginStateCopyWith<$Res> implements $LoginStateCopyWith<$Res> {
  factory _$LoginStateCopyWith(
          _LoginState value, $Res Function(_LoginState) then) =
      __$LoginStateCopyWithImpl<$Res>;
  @override
  $Res call(
      {dynamic playerID,
      dynamic userHasInvalidCred,
      dynamic username,
      dynamic password,
      dynamic isLoading,
      LoginWithCredentialsResponse? loginResponse});
}

/// @nodoc
class __$LoginStateCopyWithImpl<$Res> extends _$LoginStateCopyWithImpl<$Res>
    implements _$LoginStateCopyWith<$Res> {
  __$LoginStateCopyWithImpl(
      _LoginState _value, $Res Function(_LoginState) _then)
      : super(_value, (v) => _then(v as _LoginState));

  @override
  _LoginState get _value => super._value as _LoginState;

  @override
  $Res call({
    Object? playerID = freezed,
    Object? userHasInvalidCred = freezed,
    Object? username = freezed,
    Object? password = freezed,
    Object? isLoading = freezed,
    Object? loginResponse = freezed,
  }) {
    return _then(_LoginState(
      playerID: playerID == freezed ? _value.playerID : playerID,
      userHasInvalidCred: userHasInvalidCred == freezed
          ? _value.userHasInvalidCred
          : userHasInvalidCred,
      username: username == freezed ? _value.username : username,
      password: password == freezed ? _value.password : password,
      isLoading: isLoading == freezed ? _value.isLoading : isLoading,
      loginResponse: loginResponse == freezed
          ? _value.loginResponse
          : loginResponse // ignore: cast_nullable_to_non_nullable
              as LoginWithCredentialsResponse?,
    ));
  }
}

/// @nodoc

class _$_LoginState extends _LoginState {
  const _$_LoginState(
      {this.playerID = -1,
      this.userHasInvalidCred = true,
      this.username = "",
      this.password = "",
      this.isLoading = false,
      this.loginResponse})
      : super._();

  @JsonKey()
  @override
  final dynamic playerID;
  @JsonKey()
  @override
  final dynamic userHasInvalidCred;
  @JsonKey()
  @override
  final dynamic username;
  @JsonKey()
  @override
  final dynamic password;
  @JsonKey()
  @override
  final dynamic isLoading;
  @override
  final LoginWithCredentialsResponse? loginResponse;

  @override
  String toString() {
    return 'LoginState(playerID: $playerID, userHasInvalidCred: $userHasInvalidCred, username: $username, password: $password, isLoading: $isLoading, loginResponse: $loginResponse)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _LoginState &&
            const DeepCollectionEquality().equals(other.playerID, playerID) &&
            const DeepCollectionEquality()
                .equals(other.userHasInvalidCred, userHasInvalidCred) &&
            const DeepCollectionEquality().equals(other.username, username) &&
            const DeepCollectionEquality().equals(other.password, password) &&
            const DeepCollectionEquality().equals(other.isLoading, isLoading) &&
            const DeepCollectionEquality()
                .equals(other.loginResponse, loginResponse));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(playerID),
      const DeepCollectionEquality().hash(userHasInvalidCred),
      const DeepCollectionEquality().hash(username),
      const DeepCollectionEquality().hash(password),
      const DeepCollectionEquality().hash(isLoading),
      const DeepCollectionEquality().hash(loginResponse));

  @JsonKey(ignore: true)
  @override
  _$LoginStateCopyWith<_LoginState> get copyWith =>
      __$LoginStateCopyWithImpl<_LoginState>(this, _$identity);
}

abstract class _LoginState extends LoginState {
  const factory _LoginState(
      {dynamic playerID,
      dynamic userHasInvalidCred,
      dynamic username,
      dynamic password,
      dynamic isLoading,
      LoginWithCredentialsResponse? loginResponse}) = _$_LoginState;
  const _LoginState._() : super._();

  @override
  dynamic get playerID;
  @override
  dynamic get userHasInvalidCred;
  @override
  dynamic get username;
  @override
  dynamic get password;
  @override
  dynamic get isLoading;
  @override
  LoginWithCredentialsResponse? get loginResponse;
  @override
  @JsonKey(ignore: true)
  _$LoginStateCopyWith<_LoginState> get copyWith =>
      throw _privateConstructorUsedError;
}
