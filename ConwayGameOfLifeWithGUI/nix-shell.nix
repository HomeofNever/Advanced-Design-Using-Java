# https://discourse.nixos.org/t/how-to-create-a-development-environment-with-intellij-idea-and-openjdk/10153
# Read if jdk not working in intellij

with (import (import ./nix/sources.nix).nixpkgs) { config.allowUnfree = true; };
stdenv.mkDerivation {
  name = "idea";
  # nativeBuildInputs = [ cmake gcc  ];
  buildInputs = [ jdk jetbrains.idea-ultimate ];
}

# For pandoc, please add [ pandoc texlive.combined.scheme-full ]
# It is a huge package (latex), so it won't be added by deault