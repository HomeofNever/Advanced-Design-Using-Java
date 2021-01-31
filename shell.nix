# https://discourse.nixos.org/t/how-to-create-a-development-environment-with-intellij-idea-and-openjdk/10153
# Read if jdk not working in intellij

with (import (import ./nix/sources.nix).nixpkgs) { config.allowUnfree = true; };
stdenv.mkDerivation {
  name = "idea";
  # nativeBuildInputs = [ cmake gcc  ];
  buildInputs = [ jdk jetbrains.idea-ultimate ];
}