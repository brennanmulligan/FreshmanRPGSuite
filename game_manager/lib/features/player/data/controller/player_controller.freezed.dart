// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'player_controller.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$PlayerStateTearOff {
  const _$PlayerStateTearOff();

  _PlayerState call(
      {dynamic showResponse = false,
      CreatePlayerResponse? createPlayerResponse}) {
    return _PlayerState(
      showResponse: showResponse,
      createPlayerResponse: createPlayerResponse,
    );
  }
}

/// @nodoc
const $PlayerState = _$PlayerStateTearOff();

/// @nodoc
mixin _$PlayerState {
  dynamic get showResponse =>
      throw _privateConstructorUsedError; //@Default(true) providedInvalidInfo,
  CreatePlayerResponse? get createPlayerResponse =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $PlayerStateCopyWith<PlayerState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $PlayerStateCopyWith<$Res> {
  factory $PlayerStateCopyWith(
          PlayerState value, $Res Function(PlayerState) then) =
      _$PlayerStateCopyWithImpl<$Res>;
  $Res call({dynamic showResponse, CreatePlayerResponse? createPlayerResponse});
}

/// @nodoc
class _$PlayerStateCopyWithImpl<$Res> implements $PlayerStateCopyWith<$Res> {
  _$PlayerStateCopyWithImpl(this._value, this._then);

  final PlayerState _value;
  // ignore: unused_field
  final $Res Function(PlayerState) _then;

  @override
  $Res call({
    Object? showResponse = freezed,
    Object? createPlayerResponse = freezed,
  }) {
    return _then(_value.copyWith(
      showResponse: showResponse == freezed
          ? _value.showResponse
          : showResponse // ignore: cast_nullable_to_non_nullable
              as dynamic,
      createPlayerResponse: createPlayerResponse == freezed
          ? _value.createPlayerResponse
          : createPlayerResponse // ignore: cast_nullable_to_non_nullable
              as CreatePlayerResponse?,
    ));
  }
}

/// @nodoc
abstract class _$PlayerStateCopyWith<$Res>
    implements $PlayerStateCopyWith<$Res> {
  factory _$PlayerStateCopyWith(
          _PlayerState value, $Res Function(_PlayerState) then) =
      __$PlayerStateCopyWithImpl<$Res>;
  @override
  $Res call({dynamic showResponse, CreatePlayerResponse? createPlayerResponse});
}

/// @nodoc
class __$PlayerStateCopyWithImpl<$Res> extends _$PlayerStateCopyWithImpl<$Res>
    implements _$PlayerStateCopyWith<$Res> {
  __$PlayerStateCopyWithImpl(
      _PlayerState _value, $Res Function(_PlayerState) _then)
      : super(_value, (v) => _then(v as _PlayerState));

  @override
  _PlayerState get _value => super._value as _PlayerState;

  @override
  $Res call({
    Object? showResponse = freezed,
    Object? createPlayerResponse = freezed,
  }) {
    return _then(_PlayerState(
      showResponse:
          showResponse == freezed ? _value.showResponse : showResponse,
      createPlayerResponse: createPlayerResponse == freezed
          ? _value.createPlayerResponse
          : createPlayerResponse // ignore: cast_nullable_to_non_nullable
              as CreatePlayerResponse?,
    ));
  }
}

/// @nodoc

class _$_PlayerState extends _PlayerState with DiagnosticableTreeMixin {
  const _$_PlayerState({this.showResponse = false, this.createPlayerResponse})
      : super._();

  @JsonKey()
  @override
  final dynamic showResponse;
  @override //@Default(true) providedInvalidInfo,
  final CreatePlayerResponse? createPlayerResponse;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'PlayerState(showResponse: $showResponse, createPlayerResponse: $createPlayerResponse)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'PlayerState'))
      ..add(DiagnosticsProperty('showResponse', showResponse))
      ..add(DiagnosticsProperty('createPlayerResponse', createPlayerResponse));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _PlayerState &&
            const DeepCollectionEquality()
                .equals(other.showResponse, showResponse) &&
            const DeepCollectionEquality()
                .equals(other.createPlayerResponse, createPlayerResponse));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(showResponse),
      const DeepCollectionEquality().hash(createPlayerResponse));

  @JsonKey(ignore: true)
  @override
  _$PlayerStateCopyWith<_PlayerState> get copyWith =>
      __$PlayerStateCopyWithImpl<_PlayerState>(this, _$identity);
}

abstract class _PlayerState extends PlayerState {
  const factory _PlayerState(
      {dynamic showResponse,
      CreatePlayerResponse? createPlayerResponse}) = _$_PlayerState;
  const _PlayerState._() : super._();

  @override
  dynamic get showResponse;
  @override //@Default(true) providedInvalidInfo,
  CreatePlayerResponse? get createPlayerResponse;
  @override
  @JsonKey(ignore: true)
  _$PlayerStateCopyWith<_PlayerState> get copyWith =>
      throw _privateConstructorUsedError;
}
