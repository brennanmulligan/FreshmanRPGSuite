import 'package:companion_app/model/position_with_status.dart';
import 'package:companion_app/model/location_utilities.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:geolocator/geolocator.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'location_test.mocks.dart';


@GenerateMocks([GeoLocatorWrapper])
void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  group('location tests: ', () {

    test('We have permission with no questions asked', () async {

      MockGeoLocatorWrapper locUtil = MockGeoLocatorWrapper();
      when(locUtil.isLocationEnabled()).thenAnswer((_) async => true);
      when(locUtil.checkPermission()).thenAnswer((_) async => LocationPermission.always);
      when(locUtil.requestPermission()).thenAnswer((_) async => LocationPermission.always);
      when(locUtil.getCurrentPosition()).thenAnswer((_) async => PositionWithStatus(latitude: 1, longitude: 1));
      PositionWithStatus location = await PositionWithStatus.getCurrentLocation(locUtil);
      expect(location, isNotNull);
      expect(location.valid, isTrue);
    });

    test('When location services start disabled', () async {
      MockGeoLocatorWrapper locUtil = MockGeoLocatorWrapper();
      when(locUtil.isLocationEnabled()).thenAnswer((_) async => false);
      PositionWithStatus location = await PositionWithStatus.getCurrentLocation(locUtil);

      expect(location, isNotNull);
      expect(location.valid, isFalse);
    });

    test('When permissions start denied, but we get permission', () async {
      MockGeoLocatorWrapper locUtil = MockGeoLocatorWrapper();
      when(locUtil.isLocationEnabled()).thenAnswer((_) async => true);
      when(locUtil.checkPermission()).thenAnswer((_) async => LocationPermission.denied);
      when(locUtil.requestPermission()).thenAnswer((_) async => LocationPermission.always);
      when(locUtil.getCurrentPosition()).thenAnswer((_) async => PositionWithStatus(latitude: 1, longitude: 1));
      PositionWithStatus location = await PositionWithStatus.getCurrentLocation(locUtil);

      expect(location, isNotNull);
      expect(location.valid, isTrue);
    });
    test('When permissions start denied, and we do not get permission', () async {
      MockGeoLocatorWrapper locUtil = MockGeoLocatorWrapper();
      when(locUtil.isLocationEnabled()).thenAnswer((_) async => true);
      when(locUtil.checkPermission()).thenAnswer((_) async => LocationPermission.denied);
      when(locUtil.requestPermission()).thenAnswer((_) async => LocationPermission.denied);
      when(locUtil.getCurrentPosition()).thenAnswer((_) async => PositionWithStatus.fromFailure(message:'unchecked error message'));
      PositionWithStatus location = await PositionWithStatus.getCurrentLocation(locUtil);

      expect(location, isNotNull);
      expect(location.valid, isFalse);
    });
    test('When permissions start denied, and we get one time permission', () async {
      MockGeoLocatorWrapper locUtil = MockGeoLocatorWrapper();
      when(locUtil.isLocationEnabled()).thenAnswer((_) async => true);
      when(locUtil.checkPermission()).thenAnswer((_) async => LocationPermission.denied);
      when(locUtil.requestPermission()).thenAnswer((_) async => LocationPermission.whileInUse);
      when(locUtil.getCurrentPosition()).thenAnswer((_) async => PositionWithStatus(latitude: 1, longitude: 1));
      PositionWithStatus location = await PositionWithStatus.getCurrentLocation(locUtil);

      expect(location, isNotNull);
      expect(location.valid, isTrue);
    });

  });
}