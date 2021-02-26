pandoc header-includes.yaml manual.md -o manual.pdf --pdf-engine=lualatex
pandoc header-includes.yaml tests.md -o tests.pdf --pdf-engine=lualatex