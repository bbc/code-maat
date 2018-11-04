;;; Copyright (C) 2013 Adam Tornhill
;;;
;;; Distributed under the GNU General Public License v3.0,
;;; see http://www.gnu.org/licenses/gpl.html

(ns code-maat.analysis.contribution
  (:require [code-maat.dataset.dataset :as ds]
            [incanter.core :as incanter]))

;;; This module attempts to identify cross team pull requests
(defn cross-team-pulls
  [changes options]
  (let [pairs (ds/-group-by [:author :committer] changes)
        pull-keys (filter #(not= (get %1 :committer) "GitHub") 
                    (filter #(not= (get %1 :author) (get %1 :committer)) (keys pairs)))
        pull-pairs (select-keys pairs pull-keys)]
    (->>
      (for [[ {:keys [author committer]} ds ] pull-pairs]
       [author committer (ds/-nrows (ds/-group-by :rev ds))])
      (ds/-dataset [:author :committer :pull_requests]))
  ))
