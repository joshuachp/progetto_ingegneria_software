(TeX-add-style-hook
 "Documentazione"
 (lambda ()
   (TeX-add-to-alist 'LaTeX-provided-class-options
                     '(("article" "12pt" "a4paper")))
   (TeX-add-to-alist 'LaTeX-provided-package-options
                     '(("inputenc" "utf8") ("url" "hyphens")))
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "url")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "path")
   (add-to-list 'LaTeX-verbatim-macros-with-delims-local "url")
   (add-to-list 'LaTeX-verbatim-macros-with-delims-local "path")
   (TeX-run-style-hooks
    "latex2e"
    "article"
    "art12"
    "inputenc"
    "amsmath"
    "algorithm"
    "algorithmic"
    "fancyhdr"
    "graphicx"
    "lipsum"
    "setspace"
    "fncychap"
    "url"
    "xcolor"
    "tabularx"
    "appendix"))
 :latex)

