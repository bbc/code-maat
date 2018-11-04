;;; Copyright (C) 2013 Adam Tornhill
;;;
;;; Distributed under the GNU General Public License v3.0,
;;; see http://www.gnu.org/licenses/gpl.html

(ns code-maat.analysis.contribution-test
  (:require [code-maat.analysis.contribution :as contribution]
            [code-maat.dataset.dataset :as ds])
  (:use clojure.test))

(def ^:const options {})

(ds/def-ds changes
  [{:entity "A" :rev 1 :author "a" :committer "b" :date "2013-11-10"}
   {:entity "A" :rev 2 :author "a" :committer "a" :date "2013-11-11"}
   {:entity "B" :rev 3 :author "b" :committer "b" :date "2013-11-15"}
   {:entity "C" :rev 4 :author "c" :committer "b" :date "2013-11-23"}
   {:entity "D" :rev 4 :author "c" :committer "b" :date "2013-11-23"}
   {:entity "D" :rev 6 :author "c" :committer "b" :date "2013-11-23"}
   {:entity "E" :rev 7 :author "b" :committer "GitHub" :date "2013-11-23"}])

(deftest calculates-cross-team-pulls-based-on-authors-and-committers
  (is (= (contribution/cross-team-pulls changes options)
         (ds/-dataset [:author :committer :pull_requests]
                      [["a" "b" 1]
                       ["c" "b" 2 ]]))))
