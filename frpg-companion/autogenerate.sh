echo "Entering Autogenerate"
echo "Flutter Clean"
flutter clean
echo "Flutter pub get"
flutter pub get
echo "Flutter build"
flutter packages pub run build_runner build --delete-conflicting-outputs
echo "Exiting Autogenerate"